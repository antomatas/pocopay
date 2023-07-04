FROM postgres:13.5-bullseye

RUN sed -i '/et_EE.UTF-8/s/^# //g' /etc/locale.gen && \
    locale-gen
ENV LC_ALL et_EE.UTF-8
ENV LC_CTYPE et_EE.UTF-8
ENV LANG et_EE.utf8
ENV TZ "Europe/Tallinn"

ENV POSTGRES_DB pocopay
ENV POSTGRES_PASSWORD "postgres"

COPY ./scripts /docker-entrypoint-initdb.d
