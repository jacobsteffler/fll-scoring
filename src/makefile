all:
	javac -d ../out org/frogforce503/fllscoring/*

runc:
	java -classpath ../out org.frogforce503.fllscoring.Client

jarc:
	jar cmf MFClient ../dist/Client.jar -C ../out .
	chmod +x ../dist/Client.jar

clean:
	rm -rf ../out/org
	rm -f ../dist/*
