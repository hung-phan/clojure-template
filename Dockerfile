FROM clojure as builder

WORKDIR /opt/app

COPY project.clj .
RUN lein deps && lein cljsbuild once prefetch_dependencies
COPY . .
RUN ./scripts/build

CMD ["./scripts/start"]
