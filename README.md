# clojure-template

[![build status](https://travis-ci.org/hung-phan/clojure-template.svg?branch=master)](http://travis-ci.org/hung-phan/clojure-template/)

The idea of this repository is to try out all concepts and libraries for React.js.
Additionally, this will be the boilerplate Reframe.

## Development
Require docker and docker-compose

```bash
$ docker-compose up -d
$ lein repl
$ lein garden auto app_development # open another terminal and type in
```

In the REPL, type

```clojure
user=> (start-system)
user=> (start-browser-repl) ;; if you want to connect to js repl
```

The call to `(start-system)` starts the Figwheel server at port 3000, which takes care of
live reloading ClojureScript code and CSS. Figwheel's server will also act as
your app server, so requests are correctly forwarded to the http-handler you
define.

Running `(run-browser-repl)` starts the Weasel REPL server, and drops you into a
ClojureScript REPL. Evaluating expressions here will only work once you've
loaded the page, so the browser can connect to Weasel.

Additionaly, commands

```clojure
user=> (stop-system)
user=> (restart-system)
```

### Migration and rollback

Run `(start-system)`, then:

```clojure
user=> (-> dev-system deref :database (.migrate))
user=> (-> dev-system deref :database (.rollback))
```

### Seed data
Run `(start-system)`, then:

```clojure
user=> (-> dev-system deref :database seed-todos)
```

## Production
```bash
$ docker-compose -f docker-compose.prod.yml up
```

### Migration and rollback
```bash
$ lein repl
```

then

```clojure
user=> (def database (component/start (:database @prod-system)))
user=> (.migrate database)
user=> (.rollback database)
```

### Seed data
```clojure
user=> (def database (component/start (:database @prod-system)))
user=> (seed-todos database)
```

## Test

```bash
$ docker-compose -f docker-compose.test.yml up -d
$ lein test # lein auto test if you want to rerun the test for every change
$ lein midje
$ lein doo phantom # run clojurescript tests, requires phantomjs
$ docker-compose -f docker-compose.test.yml down
```

## Build and Start

```bash
$ ./scripts/build
$ ./scripts/start
```

Now your app is running at
[http://localhost:3000](http://localhost:3000) in production mode.

Access http://localhost:3000 to see the application

# License

[MIT License](http://en.wikipedia.org/wiki/MIT_License)
