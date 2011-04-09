#!/lusr/bin/python

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

      cgitb.enable()
      features = makeFeatureList(cgi.FieldStorage())
      tempdir = tempfile.mkdtemp("", "tmp", "tmp")
      composecmd = ["../bin/composer", "--target=../"+tempdir+"/gpl"]
      composecmd.extend(features)
      stdout += ' '.join(composecmd)
      writeLog(stdout,stderr)

      os.chdir("jak")
      proc = subprocess.Popen(composecmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
      stdout1,stderr1 = proc.communicate()   # execute process and capture output
      os.chdir(rootPath)
      stdout += stdout1 + "\n\nend compose stdout\n\ncurrent dir: " + os.getcwd() + "\n\n"
      stderr += stderr1 + "\n\nend compose stderr\n\n"
      writeLog(stdout,stderr)
      
      jak2javacmd = ["bin/jak2java"]
      for file in os.listdir(tempdir+"/gpl"):
          if file[-4:] == ".jak":
              jak2javacmd.append(tempdir + "/gpl/" + file)
      stdout += ' '.join(jak2javacmd)
      proc = subprocess.Popen(jak2javacmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
      stdout1,stderr1 = proc.communicate()   # execute process and capture output
      stdout += stdout1 + "\n\nend jak2java stdout\n\ncurrent dir: " + os.getcwd() + "\n\n"
      stderr += stderr1 + "\n\nend jak2java stderr\n\n"
      writeLog(stdout,stderr)
      
      # uncomment if you don't want to zip the .jak files
      #shutil.copytree(tempdir+"/gpl", tempdir+"/gpl/gpl", ignore=shutil.ignore_patterns('*.jak'))
      shutil.copytree(tempdir+"/gpl", tempdir+"/gpl/gpl")
      
      javaccmd = ["jdk1.7.0/bin/javac"]
      for file in os.listdir(tempdir + "/gpl/gpl"):
          if file[-5:] == ".java":
              javaccmd.append(tempdir + "/gpl/gpl/" + file)
      stdout += ' '.join(javaccmd)
      proc = subprocess.Popen(javaccmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
      stdout1,stderr1 = proc.communicate()   # execute process and capture output
      
      stdout += stdout1 + "\nend javaccmd stdout\n"
      stderr += stderr1 + "\nend javaccmd stderr\n"
      writeLog(stdout,stderr)
      
      xhtml2htmlcmd = ["jdk1.7.0/bin/java", "-classpath", "lib/OnekinUtils-Standard.jar:lib/xak.jar", "org.onekin.util.Xhtml2html", tempdir+"/gpl"]
      proc = subprocess.Popen(xhtml2htmlcmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
      stdout1,stderr1 = proc.communicate()   # execute process and capture output
      stdout += "\n\n" + ' '.join(xhtml2htmlcmd)
      stdout += stdout1 + "\nend xhtml2htmlcmd stdout\n"
      stderr += stderr1 + "\nend xhtml2htmlcmd stderr\n"
      writeLog(stdout,stderr)
      
      makeZip(tempdir)

      # set permissions for debugging
      setPermissions(tempdir)
      
      shutil.rmtree(tempdir, True)

     
def makeZip(tempdir):
      make_archive(tempdir+"/gpl", "zip", tempdir+"/gpl/gpl")

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

def setPermissions(dir):
      permissions = S_IROTH | S_IWOTH | S_IXOTH | S_IRUSR | S_IWUSR | S_IXUSR
      os.chmod(dir, permissions)
      for root, dirs, files in os.walk(dir):
          for name in files:
              os.chmod(os.path.join(root,name), permissions)
          for name in dirs:
              os.chmod(os.path.join(root,name), permissions)

def makeFeatureList(form):
    returnval = []
    features = form.keys()
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

