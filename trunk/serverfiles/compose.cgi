#!/lusr/bin/python
#stdout="test"
#stderr="teststderr"
#writeLog(stdout,stderr)
import cgi
import subprocess
import sys
import tempfile
import shutil
import os
from distutils.archive_util import make_archive
from stat import *
import cgitb
from StringIO import StringIO
import zipfile

def main():
  stdout=""
  stderr=""
  rootPath = os.getcwd()
  os.putenv("PATH", rootPath+"/jdk1.7.0/bin:"+rootPath+"/bin:.:"+os.getenv("PATH"))
#  os.putenv("CLASSPATH", rootPath+"/lib/composer.jar:.")
  try:
      cgitb.enable()
      #features = cgi.FieldStorage().keys()
#      stdout += cgi.FieldStorage()
      features = makeFeatureList(cgi.FieldStorage())
#      features.remove("_")   # remove empty items
      tempdir = tempfile.mkdtemp("", "tmp", "tmp")
      composecmd = ["../bin/composer", "--target=../"+tempdir+"/gpl"]
      composecmd.extend(features)
#      for feature in features:
#        composecmd.append(feature)
      stdout += ' '.join(composecmd)
      writeLog(stdout,stderr)

      os.chdir("jak")
      proc = subprocess.Popen(composecmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
      stdout1,stderr1 = proc.communicate()   # execute process and capture output
      os.chdir(rootPath)
      stdout += stdout1 + "\n\nend compose stdout\n\ncurrent dir: " + os.getcwd() + "\n\n"
      stderr += stderr1 + "\n\nend compose stderr\n\n"
      writeLog(stdout,stderr)
      
      jak2javacmd = ["bin/jak2java"]#, tempdir+"/*.jak"]
      for file in os.listdir(tempdir+"/gpl"):
          if file[-4:] == ".jak":
              jak2javacmd.append(tempdir + "/gpl/" + file)
      stdout += ' '.join(jak2javacmd)
      proc = subprocess.Popen(jak2javacmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
      stdout1,stderr1 = proc.communicate()   # execute process and capture output
      
      stdout += stdout1 + "\n\nend jak2java stdout\n\n" 
      stderr += stderr1 + "\n\nend jak2java stderr\n\n"
      writeLog(stdout,stderr)
      
      shutil.copytree(tempdir+"/gpl", tempdir+"/gpl/gpl", ignore=shutil.ignore_patterns('*.jak'))
      
      javaccmd = ["javac"]#, tempdir+"/gpl/*.java"]
      for file in os.listdir(tempdir + "/gpl/gpl"):
          if file[-5:] == ".java":
              javaccmd.append(tempdir + "/gpl/gpl/" + file)
      stdout += ' '.join(javaccmd)
      proc = subprocess.Popen(javaccmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
      stdout1,stderr1 = proc.communicate()   # execute process and capture output
      
      stdout += stdout1
      stderr += stderr1
      writeLog(stdout,stderr)
      
#      make_archive(tempdir+"/gpl", "zip", tempdir+"/gpl/gpl")

      setPermissions(tempdir)
      
      
      

      HEADERS = '\r\n'.join(
        [
            "Content-type: %s;",
            "Content-Disposition: attachment; filename=%s",
            "Content-Title: %s",
            "Content-Length: %i",
            "\r\n", # empty line to end headers
        ]
      )

      b = StringIO()
      z = zipfile.ZipFile(b, 'w', zipfile.ZIP_DEFLATED)
      for root, dirs, files in os.walk(tempdir+"/gpl/gpl"):
          for file in dirs:
              name = os.path.join(root,file)
              z.write(name,name[name.rfind("gpl"):])
          for file in files:
              name = os.path.join(root,file)
              z.write(name,name[name.rfind("gpl"):])

      z.close()
      length = b.tell()
      b.seek(0)
      sys.stdout.write(HEADERS % ('application/zip', 'gpl.zip', 'gpl.zip', length))
      sys.stdout.write(b.read())
      b.close()

#      print "Content-Type:application/zip; name=\"gpl.zip\"\r\n"
      #print "Content-Disposition: attachment; filename=\"gpl.zip\"\r\n\n"
#      zip = open(tempdir+"/gpl/gpl.zip", "rb")
#      str = zip.read();
#      print str
#      zip.close()
  finally:
     pass
     # writeLog(stdout,stderr)

def setPermissions(dir):
      permissions = S_IROTH|S_IWOTH|S_IXOTH|S_IRUSR|S_IWUSR|S_IXUSR
      for root, dirs, files in os.walk(dir):
          for name in files:
              os.chmod(os.path.join(root,name), permissions)
          for name in dirs:
              os.chmod(os.path.join(root,name), permissions)

def makeFeatureList(form):
    returnval = []
    features = form.keys()
#    for feature in features:
#        returnval.append(feature+" = " +form[feature].value)
    for i in range(len(features)):
        for feature in features:
            if int(form[feature].value) == i+1:
                returnval.append(feature)
                break
    return returnval
  
    
      
def writeLog(stdout, stderr):
      fout = open("tmp/out.txt", "w")
      if(stdout != None):
        fout.write(stdout)
      else:
        fout.write("stdout == None")
      fout.write("\n\n\n\n")
      if(stderr != None):
        fout.write(stderr)
      else:
        fout.write("stderr == None")
      fout.close()
  
main()

