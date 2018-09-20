Building on linux prerequisites:

sudo apt-get install openjdk-8-jre git maven

Linux build guide:

git clone https://github.com/JakubGornickii/orderReader.git
cd orderReader
chmod 777 run.sh compile.sh
./compile.sh &&./run.sh

Building on Windows prerequisites:

-Maven
-Java 8

Windows build quide:

build.bat && run.bat


Loading orders:

write full path of file for example C:\Users\Admin\Desktop\orders\orders.xml
or put files to orderReader folder and write only file name with extension. for example orders.csv