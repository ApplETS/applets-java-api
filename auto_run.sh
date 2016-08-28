./gradlew build
cp --remove-destination build/libs/applets-java-api.war docker-dir/web/
docker-compose -f docker-dir/docker-compose.yml up
