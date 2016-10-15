FROM clojure

MAINTAINER Hung Phan

ENV CLJ_ENV=production \
    PORT=3000

WORKDIR /opt/app

COPY project.clj .

RUN lein deps

COPY . .

RUN ./scripts/build

CMD ["./scripts/start"]
