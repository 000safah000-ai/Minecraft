import urllib.request
import ssl

ctx = ssl.create_default_context()
ctx.check_hostname = False
ctx.verify_mode = ssl.CERT_NONE

req = urllib.request.Request("https://maven.blamejared.com/foundry/veil/veil-fabric-1.21.1/3.1.2/veil-fabric-1.21.1-3.1.2.pom")
try:
    with urllib.request.urlopen(req, context=ctx) as response:
        print(response.read().decode('utf-8'))
except Exception as e:
    print(e)
