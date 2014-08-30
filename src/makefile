filesc=-C ../out rsc/Roboto-Light.ttf -C ../out org/frogforce503/fllscoring/Client.class -C ../out org/frogforce503/fllscoring/Clock.class -C ../out org/frogforce503/fllscoring/ClientPanel\$$TablePanel.class -C ../out org/frogforce503/fllscoring/Team.class -C ../out org/frogforce503/fllscoring/ClientPanel.class
filess=-C ../out org/frogforce503/fllscoring/Server.class -C ../out org/frogforce503/fllscoring/Team.class

all:
	javac -d ../out org/frogforce503/fllscoring/*

runc:
	java -classpath ../out org.frogforce503.fllscoring.Client

runs:
	java -classpath ../out org.frogforce503.fllscoring.Server

jarc:
	jar cmf MFClient ../dist/Client.jar $(filesc)
	chmod +x ../dist/Client.jar

jars:
	jar cmf MFServer ../dist/Server.jar $(filess)
	chmod +x ../dist/Server.jar

clean:
	rm -rf ../out/org
	rm -f ../dist/*
