#!/lusr/bin/python
import cgi
import subprocess
#import cgitb
import sys
def main():
  #cgitb.enable()
  print "Content-type: application/json\n"
  userSelections = cgi.FieldStorage().keys()
  modelName = cgi.FieldStorage()["modelname"].value
  userSelections.remove("modelname")
  userSelections.remove("_")
  javacmd = ["jdk1.7.0/bin/java", "-classpath", ".:lib/guidsl.jar:lib/sat4j.jar:lib/jakarta.jar:lib/gson-1.6.jar", "webGuiDsl.WebGuidsl", "-m", modelName]
  for feature in userSelections:
    javacmd.append(feature)
  proc = subprocess.Popen(javacmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
  stdout,stderr = proc.communicate()   # execute process and capture output
  stdout = stdout.replace("\\n", "<br>")
  if stdout != None:
    print stdout
  else:
    print "none"
  
  # write to log for debugging
  # fout = open("tmp/out.txt", "w")
  # if(userSelections != None):
    # fout.write(str(userSelections))
  # else:
    # fout.write("no userselections")
  # fout.write("\n\n\n" + str(javacmd))
  # if(stdout != None):
    # fout.write("\n\n" + stdout)
  # else:
    # fout.write("stdout == None")
  # fout.write("\n\n\n\n")
  # if(stderr != None):
    # fout.write(stderr)
  # else:
    # fout.write("stderr == None")
  # fout.close()

main()

