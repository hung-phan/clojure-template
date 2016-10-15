FROM clojure

MAINTAINER Hung Phan

ENV CLJ_ENV=production \
    PORT=3000

WORKDIR /opt/app

COPY project.clj .

RUN lein deps

RUN lein cljsbuild once prefetch_dependencies

COPY . .

RUN ./scripts/build

CMD ["./scripts/start"]
