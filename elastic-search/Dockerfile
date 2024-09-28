# Usar la imagen oficial de Elasticsearch como base
FROM docker.elastic.co/elasticsearch/elasticsearch:8.5.1

# Copiar el keystore y la configuración al contenedor
COPY elasticsearch.jks /usr/share/elasticsearch/config/elasticsearch.jks
COPY elasticsearch.yaml /usr/share/elasticsearch/config/elasticsearch.yml
