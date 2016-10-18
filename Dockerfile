FROM clojure

MAINTAINER Hung Phan

WORKDIR /opt/app

COPY project.clj .

RUN lein deps

RUN lein cljsbuild once prefetch_dependencies

COPY . .

ENV CLJ_ENV=production \
    PORT=3000

RUN ./scripts/build

CMD ["./scripts/start"]
