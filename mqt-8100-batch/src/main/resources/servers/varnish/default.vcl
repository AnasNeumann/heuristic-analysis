#
# This is an example VCL file for Varnish.
#
# It does not do anything by default, delegating control to the
# builtin VCL. The builtin VCL is called when there is no explicit
# return statement.
#
# See the VCL chapters in the Users Guide at https://www.varnish-cache.org/docs/
# and https://www.varnish-cache.org/trac/wiki/VCLExamples for more examples.

# Marker to tell the VCL compiler that this VCL has been adapted to the
# new 4.0 format.
vcl 4.0;

# Default backend definition. Set this to point to your content server.
backend default {
    .host = "164.132.113.59";
    .port = "1234";
}
backend jenkins {
    .host = "164.132.113.59";
    .port = "9280";
}
backend wsjos {
    .host = "164.132.113.59";
    .port = "3456";
}
backend nginx {
    .host = "164.132.113.59";
    .port = "1234";
}

sub vcl_synth {
    if (resp.status == 301 || resp.status == 302) {
        set resp.http.location = resp.reason;
        set resp.reason = "Moved";
        return (deliver);
    }
}

sub vcl_recv {
    # Happens before we check if we have this in cache already.
    #
    # Typically you clean up the request here, removing cookies you don't need,
    # rewriting the request, etc.
    if (req.url ~ "well-known") {
        set req.backend_hint = nginx;
    } else if (req.http.X-Forwarded-Proto !~ "https" || req.http.host !~ "www") {
        return (synth(302, "https://www.uprodit.com/mqt-8100" + req.url));
    } else if (req.url ~ "jenkins") {
        set req.backend_hint = jenkins;
    } else if (req.url ~ "wsjos") {
        set req.url = regsub(req.url, "wsjos", "");
        set req.backend_hint = wsjos;
    }
}

sub vcl_backend_response {
    # Happens after we have read the response headers from the backend.
    #
    # Here you clean the response headers, removing silly Set-Cookie headers
    # and other mistakes your backend does.
}

sub vcl_deliver {
    # Happens when we have all the pieces we need, and are about to send the
    # response to the client.
    #
    # You can do accounting or modifying the final object here.
}

