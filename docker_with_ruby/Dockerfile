FROM mcr.microsoft.com/azure-functions/dotnet:3.0-appservice
ENV AzureWebJobsScriptRoot=/home/site/wwwroot \
    AzureFunctionsJobHost__Logging__Console__IsEnabled=true

RUN apt update && \
    apt install -y \
    git \
    autoconf \
    bison \
    build-essential \
    libssl-dev \
    libyaml-dev \
    libreadline6-dev \
    zlib1g-dev \
    libncurses5-dev \
    libffi-dev \
    libgdbm6 \
    libgdbm-dev \
    libdb-dev \
    && rm -rf /var/lib/apt/lists/*

RUN git clone --depth=1 https://github.com/rbenv/ruby-build && PREFIX=/usr/local ./ruby-build/install.sh && rm -rf ruby-build
RUN ruby-build 2.7.2 /usr/local

WORKDIR /home/site/wwwroot

COPY Gemfile /home/site/wwwroot/
RUN bundler install
COPY . /home/site/wwwroot/

ENV AzureWebJobsStorage=$AzureWebJobsStorage
