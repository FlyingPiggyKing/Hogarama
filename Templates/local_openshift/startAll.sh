#!/bin/sh

set -x

oc cluster up  --skip-registry-check=true --version="v3.6.173.0.146"
oc login -u system:admin
oc policy add-role-to-user system:image-pusher developer
oc create -f alltemplates.yaml -n openshift
oc login -u developer
oc new-project hogarama
oc create is hogajama
oc create is fluentd
OPENSHIFT_TOKEN=$(oc whoami -t)
oc process -f hogaramaOhneHost.yaml OPENSHIFT_AUTH_TOKEN=$OPENSHIFT_TOKEN | oc create -f -
oc process -f ../sso/sso-app-secret.yaml | oc create -f -
oc process -f ../sso/sso-service-account.yaml | oc create -f -
oc process -f ../sso/sso.yaml | oc create -f -

oc process -f prometheus.yaml | oc create -f -
oc process -f grafana.yaml | oc create -f -

GRAFANA_ROUTE=$(oc get routes -l app=grafana | awk 'NR==2 {print $2}')
PROMETHEUS_ROUTE=$(oc get routes -l app=prometheus | awk 'NR==2 {print $2}')
sed "s/\$GRAFANA_HOST/$GRAFANA_ROUTE/g" configmaps.yaml | sed "s/\$PROMETHEUS_HOST/$PROMETHEUS_ROUTE/g" | oc process -f - | oc create -f -
