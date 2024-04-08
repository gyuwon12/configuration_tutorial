# 1. Download ubuntu 22.04.
# Use Ubuntu 22.04 image from dockerhub
FROM ubuntu:22.04

# Before start install packages, it is necessary to update Ubuntu's package list to the latest.
RUN apt-get -y -q update

# 2. Install all necessary packages to run your program, such as vim, java 17, maven, etc.
# -y means all anwser is 'YES'
RUN apt-get install -y vim openjdk-17-jdk maven
# other packages
RUN apt-get install -y curl wget gnupg2
RUN rm -rf /var/lib/apt/lists/*

# 3. Create /root/project directory and set it as WORKDIR.
# RUN, CMD, ENTRYPOINT, COPY, and ADD commands are executed based on this location.
WORKDIR /root/project

# 4 & 5. Add your ‘milestone1 directory’ under WORKDIR. & Add your run.sh file under WORKDIR.
# Add 'milestone1 directory' under WORKDIR and give execution rights to the run.sh
COPY milestone1 /root/project/milestone1
COPY make.sh /root/project/make.sh
RUN chmod +x /root/project/make.sh

# Import MongoDB public GPG key
RUN wget -qO - https://www.mongodb.org/static/pgp/server-6.0.asc | apt-key add -

# Add MongoDB repository to sources list
RUN echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu jammy/mongodb-org/6.0 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-6.0.list

# Update package list and install MongoDB
RUN apt-get update \
    && apt-get install -y mongodb-org \
    && rm -rf /var/lib/apt/lists/*

# Create necessary directories
RUN mkdir -p /data/db /data/configdb

# Change ownership of directories
RUN chown -R mongodb:mongodb /data/db /data/configdb

# 6. A container should execute a bash shell by default when the built image is launched.
# When the image is run, execute a bash shell
CMD ["/bin/bash"]
