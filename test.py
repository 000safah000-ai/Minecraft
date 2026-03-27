import json
import urllib.request
import re

def check_url(url):
    print("Checking", url)
    try:
        req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
        html = urllib.request.urlopen(req, timeout=5).read().decode('utf-8')
        links = re.findall(r'href="([^"]+)"', html)
        for link in links:
            if "/" in link or ".jar" in link or ".xml" in link:
                print("  -", link)
    except Exception as e:
        print("  Error:", e)

check_url("https://maven.blamejared.com/foundry/veil/")
check_url("https://maven.blamejared.com/foundry/veil/veil-fabric-1.21.1/")
check_url("https://maven.blamejared.com/foundry/veil/veil-1.21.1-fabric/")
check_url("https://maven.suel.dev/foundry/veil/")
check_url("https://maven.foundrymc.com/releases/foundry/veil/")
