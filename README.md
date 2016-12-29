# clojure-template
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
$ docker-compose -f docker-compose.test.yml -d up
$ lein test # lein auto test if you want to rerun the test for every change
$ lein midje
$ lein doo phantom # run clojurescript tests, requires phantomjs
$ docker-compose -f docker-compose.test.yml down
```

## Deploying to Heroku

This assumes you have a
[Heroku account](https://signup.heroku.com/dc), have installed the
[Heroku toolbelt](https://toolbelt.heroku.com/), and have done a
`heroku login` before.

``` sh
git init
git add -A
git commit
heroku create
git push heroku master:master
heroku open
```

## Running with Foreman

Heroku uses [Foreman](http://ddollar.github.io/foreman/) to run your
app, which uses the `Procfile` in your repository to figure out which
server command to run. Heroku also compiles and runs your code with a
Leiningen "production" profile, instead of "dev". To locally simulate
what Heroku does you can do:

```bash
$ ./scripts/build
$ ./scripts/start
```

Now your app is running at
[http://localhost:3000](http://localhost:3000) in production mode.

Access http://localhost:3000 to see the application

# License

[MIT License](http://en.wikipedia.org/wiki/MIT_License)
