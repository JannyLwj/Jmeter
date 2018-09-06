FROM java:8
MAINTAINER Lina, Liu

RUN apt-get update && apt-get install -y curl ca-certificates software-properties-common python3-pip

# Install jMeter
ENV JMETER_DOWNLOAD_URL https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-4.0.tgz
RUN	mkdir /jmeter | curl -L --progress ${JMETER_DOWNLOAD_URL} | tar -xz --strip=1 -C /jmeter
COPY jmeter_plugin/jmerter-nuance-tts.jar /jmeter/lib/ext

# Install Git
RUN  git config --global user.email "ncs_cd_qa@local.host"
RUN  git config --global user.name "ncs_cd_qa"
COPY /ssh/ /root/.ssh
RUN chmod 400 /root/.ssh/id_rsa

RUN mkdir /workspace
COPY launcher.py /workspace
COPY requirements.txt /workspace
RUN pip3 install -r /workspace/requirements.txt


CMD ["python3", "/workspace/launcher.py"]