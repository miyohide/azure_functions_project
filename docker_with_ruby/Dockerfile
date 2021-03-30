FROM mcr.microsoft.com/azure-functions/dotnet:3.0-appservice
ENV AzureWebJobsScriptRoot=/home/site/wwwroot \
    AzureFunctionsJobHost__Logging__Console__IsEnabled=true

RUN apt update && \
    apt install -y ruby

WORKDIR /home/site/wwwroot

RUN gem install bundler --no-doc

COPY Gemfile /home/site/wwwroot/
RUN bundler install
COPY . /home/site/wwwroot/
