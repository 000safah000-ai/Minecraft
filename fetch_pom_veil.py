import urllib.request
import urllib.error
import xml.etree.ElementTree as ET

url = "https://maven.foundrymc.com/releases/foundry/veil/veil-fabric-1.21.1/3.0.2/veil-fabric-1.21.1-3.0.2.pom"
try:
    response = urllib.request.urlopen(url)
    xml_data = response.read()
    root = ET.fromstring(xml_data)
    namespace = {"mvn": "http://maven.apache.org/POM/4.0.0"}
    
    print("Dependencies:")
    dependencies = root.find("mvn:dependencies", namespace)
    if dependencies is not None:
        for dep in dependencies.findall("mvn:dependency", namespace):
            group = dep.findtext("mvn:groupId", namespaces=namespace)
            artifact = dep.findtext("mvn:artifactId", namespaces=namespace)
            version = dep.findtext("mvn:version", namespaces=namespace)
            scope = dep.findtext("mvn:scope", namespaces=namespace)
            print(f"- {group}:{artifact}:{version} (scope: {scope})")
    else:
        print("No dependencies found.")
except Exception as e:
    print("Error:", e)