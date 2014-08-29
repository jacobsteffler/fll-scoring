all:
	javac -d ../out org/frogforce503/fllscoring/*

runc:
	java -classpath ../out org.frogforce503.fllscoring.Client

runs:
	java -classpath ../out org.frogforce503.fllscoring.Server

jarc:
	jar cmf MFClient ../dist/Client.jar -C ../out .
	chmod +x ../dist/Client.jar

jars:
	jar cmf MFServer ../dist/Server.jar -C ../out .
	chmod +x ../dist/Server.jar

clean:
	rm -rf ../out/org
	rm -f ../dist/*
