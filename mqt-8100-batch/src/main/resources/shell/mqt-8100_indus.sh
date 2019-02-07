#!/bin/bash
#Author: Anas Neumann <anas.neumann.isamm@gmail.com>
#Author: Idriss NEUMANN <neumann.idriss@gmail.com>

GIT_REPO_URL="http://mqt-8100@91.121.80.56:8181/gitlab/comwork/mqt-8100.git"
INSTALL_DIR="/BACK_MQT-8100"
SAVE_DIR="${INSTALL_DIR}/old"
GIT_DIR="${INSTALL_DIR}/export_git"
BATCH_DIR="${GIT_DIR}/mqt-8100-batch"
SHELL_DIR="${BATCH_DIR}/src/main/resources/shell"
TOMCAT_VERSION="8.5.15"
MQT-8100EPHYTO_BACK_LOGFILE="${INSTALL_DIR}/mqt-8100-back.log"
ANGULAR_URL_PPD="https://www.uprodit.com/mqt-8100/"
ANGULAR_URL_PROD="https://www.uprodit.com/mqt-8100/"
ENV="no"

usage() {
	echo "Usage: ./livraison.sh [options]"
	echo "-h : afficher l'aide"
	echo "-u : mettre à jour le script de livraison"
	echo "-b : fabriquer les artifacts à partir du master (utiliser avec --ppd ou --prod)"
	echo "-d : deploy de l'artifact back"
	echo "-p : purger les logs"
	echo "--ppd  : spécifier que l'environnement est celui de ppd"
	echo "--prod : spécifier que l'environnement est celui de production"
	echo "--repair : réparer les checksums de la bdd"
	echo "--bdd : mettre à jour la bdd"
	echo "--backup : backup de la base de données PostgreSQL (avec rotation)"
	echo "--bashrc : mettre à jour le fichier bashrc"
	echo "--centos : mettre à jour la conf centos"
	echo "--varnish : mettre à jour la conf varnish"
    echo "--nginx : mettre à jour la conf nginx"
}

error(){
	echo "Erreur : parametres invalides !" >&2
	echo "Utilisez l'option -h pour en savoir plus" >&2
	exit 1
}

git_update(){
	[[ $1 ]] && branche=$1 || branche="master"
	
	if [[ ! -d "$GIT_DIR" ]]; then
		git clone "$GIT_REPO_URL" "$GIT_DIR"
		cd "$GIT_DIR"
		git config credential.helper store
	fi
	
	cd "$GIT_DIR"
	
	git stash
	git stash clear
	git checkout "$branche"
	git stash
	git stash clear
	git pull
	chmod +x "${SHELL_DIR}/letsrenew.sh"
}

install_bdd(){
	git_update "$3"
	cd "$BATCH_DIR"
	mvn install pre-integration-test -Dmaven.test.skip=false -Dpurge.skip=$1 -Dipbd=localhost -Dusername=mqt-8100_admin -Dpassword="styldecor7\$pwd" -Ddbname=mqt-8100"$2" flyway:migrate
	rm -rf /conf-webapps/mqt-8100_cache*
}

repair_bdd(){
	git_update "$2"
	cd "$BATCH_DIR"
	mvn install pre-integration-test -Dmaven.test.skip=false -Dpurge.skip=true -Dipbd=localhost -Dusername=mqt-8100_admin -Dpassword=mqt-8100 -Ddbname=mqt-8100"$1" flyway:repair
}

purge_log(){
	for file in /usr/local/apache-tomcat-${TOMCAT_VERSION}-*/logs/*; do 
		[[ $(echo $file|grep -Eo "[0-9]{4}-[0-9]{2}-[0-9]{2}") < $(date +"%Y-%m-%d") ]] && rm -rf $file
	done
	
	rm -rf /usr/local/apache-tomcat-${TOMCAT_VERSION}-jenkins/logs/*
    echo "" > ${MQT-8100EPHYTO_BACK_LOGFILE}

	# Pour en finir avec le $? = 1 lorsque pas de résultats
	echo "done"
}

# maj du script courrant
update(){
	path_install="${INSTALL_DIR}/install.sh"
	echo "-------------------------------------------------------"
	echo "----------Mise à jour du script install.sh"
	git_update "$1"
	cp -f "${SHELL_DIR}/mqt-8100_indus.sh" "$path_install"
	dos2unix "$path_install"
	chmod +x "$path_install"
}

# maj du script courrant
pgbackup(){
	echo "-------------------------------------------------------"
	echo "----------Mise à jour des scripts SH"
	git_update "$1"
	
	for i in "${BATCH_DIR}/src/main/resources/shell/pgdump"/*; do
	    dos2unix "$i"
	    chmod +x "$i"
	done
	
	bash "${BATCH_DIR}/src/main/resources/shell/pgdump/pg_backup_rotated.sh"
}

deploy_back(){
        cd "$INSTALL_DIR"
	path_war="./"
	
	war="$(find "$path_war" -iname "mqt-8100-ws*.jar"|head -1)"
	war2="./mqt-8100-ws.jar"
	
	if [[ ! -f $war ]]; then
	    echo "War inexistant ! war = ${war}, pwd = $(pwd)" >&2
            exit 1
	fi
	
	[[ "$war" != "$war2" ]] && cp "$war" "$war2"
	
	echo "Arret du composant mqt-8100-ws"
	ps -ef | grep -i mqt-8100 | grep java | awk '{print $2}' | while read -r; do kill -9 "$REPLY"; done
	
	echo "Démarrage du composant mqt-8100"
	chmod +x "$war2"
	nohup java -jar "$war2" >> ${MQT-8100EPHYTO_BACK_LOGFILE} 2>&1 &
	disown
}

war_trunk(){
	VERSION="trunk"

	if [[ $ENV == "ppd" ]]; then
	    angular_url="$ANGULAR_URL_PPD"
	elif [[ $ENV == "prod" ]]; then
	    angular_url="$ANGULAR_URL_PROD"
	else
	    error
	fi
	
	echo "----------DEBUT----------------------------------------"
	echo "----------Environment: ${ENV}"
	echo "----------Nettoyage de la version precedente"
	rm -rf *.war
	rm -rf *.jar

	echo "-------------------------------------------------------"
	echo "----------Pull master"
	git_update "$1"
	
	currentPath="$(pwd)"
	cd "$GIT_DIR"
	
	echo "-------------------------------------------------------"
	echo "----------Changement des fichiers de conf"
	pathEnv="./mqt-8100-batch/src/main/resources/env/${ENV}"
	ls -l ${pathEnv}/ws/*
	ls -l ${pathEnv}/ui/*
	cp ${pathEnv}/ws/* mqt-8100-ws/src/main/resources
	cp ${pathEnv}/ui/* mqt-8100-ui/src
	
	echo "-------------------------------------------------------"
	echo "----------Build de mqt-8100-ws"
	mvn clean package -Dmaven.test.skip=true

	echo "-------------------------------------------------------"
        echo "----------Build de mqt-8100-ui"
        cd "./mqt-8100-ui"
        npm install
        rm -rf dist/*
        ng build --prod --no-aot --base-href "$angular_url" --deploy-url "$angular_url"
        cd ..
	
	WAR="mqt-8100-ws.jar"
	pathWAR="${GIT_DIR}/mqt-8100-ws/target/mqt-8100-ws-0.0.1-SNAPSHOT.jar"
	if [[ ! -f $pathWAR ]]; then
	    echo "Erreur : le fichier $WAR n'existe pas..." >&2
	    retour="1"
	else
        cp "$pathWAR" "./$WAR"
        chmod -R 777 "./$WAR"
	    retour="0"
	fi
	
	echo "----------FIN------------------------------------------"
	exit "$retour"
}

# Safely replace a file from git src
replace_file() {
    src="${2}/${1}"
    [[ $4 ]] && name="$4" || name="$1"
    [[ $4 ]] && target="${3}/${4}" || target="${3}/${1}"

    if [[ -f $src ]]; then
        echo "----------Mise à jour de ${name}"
        cp -f  "$src" "$target"
        dos2unix "$target"
        chmod 744 "$target"
    fi
}

centos(){
	path_install=""
    echo "-------------------------------------------------------"
    echo "----------Mise à jour de la conf centos"
    git_update "$1"

    src_dir="${BATCH_DIR}/src/main/resources/servers/centos"

    echo "----------Mise à jour de la conf sysctl"
    name="sysctl.conf"
	target_name="$name"
	target_dir="/etc"
	replace_file "$name" "$src_dir" "$target_dir" "$target_name"
	sysctl -p "${target_dir}/${target_name}"

    echo "----------Mise à jour de /etc/security/limits.conf"
	name="limits.conf"
    target_name="$name"
    target_dir="/etc/security"
    replace_file "$name" "$src_dir" "$target_dir" "$target_name"

    ulimit -n 65535
}

varnish(){
    echo "-------------------------------------------------------"
    echo "----------Mise à jour de varnish"
    git_update "$1"
    name="default.vcl"
    src_dir="${BATCH_DIR}/src/main/resources/servers/varnish"
    target_dir="/etc/varnish"
    replace_file  "$name" "$src_dir" "$target_dir"

    ps -ef|grep -i varnish|awk '{print $2}'|xargs kill -9 || :
    varnishd -f "${target_dir}/${name}"
}

nginx(){
	echo "-------------------------------------------------------"
    echo "----------Mise à jour de nginx"
    git_update "$1"

    src_dir="${BATCH_DIR}/src/main/resources/servers/nginx"
    replace_file "nginx.conf" "$src_dir" "/etc/nginx"

    systemctl restart nginx
}

bashrc(){
	path_install=""
    echo "-------------------------------------------------------"
    echo "----------Mise à jour du bashrc"
    git_update "$1"

    name="bashrc"
	target_name=".bashrc"
	src_dir="${BATCH_DIR}/src/main/resources/servers/centos"
	target_dir="/root"
	replace_file "$name" "$src_dir" "$target_dir" "$target_name"
	source "${target_dir}/${target_name}"
}

[[ $# -lt 1 ]] && error

options=$(getopt -o u,h,b,d,p -l help,update,build,deploy,bdd,updatebdd,backup,repair,ppd,prod,centos,varnish,nginx,bashrc -- "$@")
set -- $options 
while true; do 
    case "$1" in 
        -u|--update) update ; shift ;;
        -h|--help) usage ; shift ;;
        -b|--build) war_trunk ; shift ;;
        -d|--deploy) deploy_back ; shift ;;
        -p|--purgelog) purge_log ; shift ;;
        --bdd|--updatebdd) install_bdd true "" ; shift ;;
        --repair) repair_bdd "" ; shift ;;
        --backup) pgbackup ; shift ;;
        --prod) ENV="prod" ; shift ;;
        --ppd)  ENV="ppd"  ; shift ;;
        --centos) centos; shift ;;
        --varnish) varnish; shift ;;
        --nginx) nginx; shift ;;
        --bashrc) bashrc; shift ;;
        --) shift ; break ;; 
        *) error ; shift ;;
    esac 
done

