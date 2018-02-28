FROM clojure as builder

WORKDIR /opt/app

COPY project.clj .
RUN lein deps && lein cljsbuild once prefetch_dependencies
COPY . .
RUN ./scripts/build


FROM clojure

WORKDIR /opt/app

COPY --from=builder /opt/app/target /opt/app/target
COPY --from=builder /opt/app/resources /opt/app/resources
COPY --from=builder /opt/app/scripts /opt/app/scripts

CMD ["./scripts/start"]
