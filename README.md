# ETag Demo

Steps to reproduce
https://github.com/AndreasKostler/ring.middleware.etag/issues/3. Tested on
Ubuntu Linux 14.04 with JDK 8, Clojure 1.6, lein 2.3.4

1. Start the app with (check that it starts on port 3000):

        $ lein ring server

2. Use firefox/curl/wget to navigate to `http://localhost:3000/etag/test_file.txt` - check that the Etag header is returned ok. 

3. CTRL-C the app

4. Next, package the app into a standalone jar file:

        $ LEIN_SNAPSHOTS_IN_RELEASE=1 lein ring uberjar

5. Once it has built, run (check that it starts on port 3000):

        $ java -jar target/etag-demo-0.0.1-standalone.jar
    
6. Now Use firefox/curl/wget to navigate to
`http://localhost:3000/etag/test-file.txt` again - this time an exception
occurs:

        2014-06-02 22:26:08.014:WARN:oejs.AbstractHttpConnection:/etag/test_file.txt
        java.lang.IllegalArgumentException: No method in multimethod 'calculate-etag' for dispatch value: class sun.net.www.protocol.jar.JarURLConnection$JarURLInputStream
            at clojure.lang.MultiFn.getFn(MultiFn.java:160)
            at clojure.lang.MultiFn.invoke(MultiFn.java:231)
            at ring.middleware.etag.core$create_hashed_etag_fn$fn__781.invoke(core.clj:27)
            at ring.middleware.etag.core$cached_response$fn__786.invoke(core.clj:39)
            at ring.middleware.etag.core$with_etag$fn__791.invoke(core.clj:51)
            at compojure.core$routing$fn__1324.invoke(core.clj:112)
            at clojure.core$some.invoke(core.clj:2515)
            at compojure.core$routing.doInvoke(core.clj:112)
            at clojure.lang.RestFn.applyTo(RestFn.java:139)
            at clojure.core$apply.invoke(core.clj:626)
            at compojure.core$routes$fn__1328.invoke(core.clj:117)
            at ring.middleware.keyword_params$wrap_keyword_params$fn__43.invoke(keyword_params.clj:32)
            at ring.middleware.nested_params$wrap_nested_params$fn__85.invoke(nested_params.clj:75)
            at ring.middleware.params$wrap_params$fn__1502.invoke(params.clj:58)
            at ring.middleware.multipart_params$wrap_multipart_params$fn__122.invoke(multipart_params.clj:107)
            at ring.middleware.flash$wrap_flash$fn__680.invoke(flash.clj:31)
            at ring.middleware.session$wrap_session$fn__667.invoke(session.clj:85)
            at clojure.lang.Var.invoke(Var.java:379)
            at ring.adapter.jetty$proxy_handler$fn__71.invoke(jetty.clj:20)
            at ring.adapter.jetty.proxy$org.eclipse.jetty.server.handler.AbstractHandler$ff19274a.handle(Unknown Source)
            at org.eclipse.jetty.server.handler.HandlerWrapper.handle(HandlerWrapper.java:116)
            at org.eclipse.jetty.server.Server.handle(Server.java:369)
            at org.eclipse.jetty.server.AbstractHttpConnection.handleRequest(AbstractHttpConnection.java:486)
            at org.eclipse.jetty.server.AbstractHttpConnection.headerComplete(AbstractHttpConnection.java:933)
            at org.eclipse.jetty.server.AbstractHttpConnection$RequestHandler.headerComplete(AbstractHttpConnection.java:995)
            at org.eclipse.jetty.http.HttpParser.parseNext(HttpParser.java:644)
            at org.eclipse.jetty.http.HttpParser.parseAvailable(HttpParser.java:235)
            at org.eclipse.jetty.server.AsyncHttpConnection.handle(AsyncHttpConnection.java:82)
            at org.eclipse.jetty.io.nio.SelectChannelEndPoint.handle(SelectChannelEndPoint.java:668)
            at org.eclipse.jetty.io.nio.SelectChannelEndPoint$1.run(SelectChannelEndPoint.java:52)
            at org.eclipse.jetty.util.thread.QueuedThreadPool.runJob(QueuedThreadPool.java:608)
            at org.eclipse.jetty.util.thread.QueuedThreadPool$3.run(QueuedThreadPool.java:543)
            at java.lang.Thread.run(Thread.java:745)

7. This exception occurs because the resource is being served from within the
jar file, and there is no calculate-etag dispatcher for input streams.

8. I have had a look at this and _almost_ have a working version which
involves rewinding BufferedInputStreams (it's not pretty, but it nearly works)

9. Note: the code uses the published etags jar from clojars, which slightly differs from that on the ring-middleware-etags github page
