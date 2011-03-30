#!/lusr/bin/python
import cgi
import subprocess
#import cgitb
import sys
def main():
  #cgitb.enable(display=0, logdir="tmp")
  print "Content-type: application/json\n"
  userSelections = cgi.FieldStorage().keys()
  userSelections.remove("_")   # remove empty items
  javacmd = ["jdk1.7.0/bin/java", "-classpath", ".:guidsl.jar:sat4j.jar:jakarta.jar:gson-1.6.jar", "WebGuidsl", "-m", "gpl.m"]
  for feature in userSelections:
    javacmd.append(feature)
  proc = subprocess.Popen(javacmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
  stdout,stderr = proc.communicate()   # execute process and capture output
  stdout = stdout.replace("\\n", "<br>")
  if stdout != None:
    print stdout
  else:
    print "none"
  
  fout = open("tmp/out.txt", "w")
  if(userSelections != None):
    fout.write(str(userSelections))
  else:
    fout.write("no userselections")
  fout.write("\n\n\n" + str(javacmd))
  if(stdout != None):
    fout.write("\n\n" + stdout)
  else:
    fout.write("stdout == None")
  fout.write("\n\n\n\n")
  if(stderr != None):
    fout.write(stderr)
  else:
    fout.write("stderr == None")
  fout.close()

main()

