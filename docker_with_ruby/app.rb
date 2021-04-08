require 'sinatra/base'

class MyApp < Sinatra::Base
  configure do
    enable :logging
    set :environment, :production
    set :port, ENV['FUNCTIONS_CUSTOMHANDLER_PORT']
  end

  post '/TimerExample' do
    logger.info "aaaaaaaaaaaaaa"
    logger.info "----- [#{request.env}]"
  end

  get '/api/Hello' do
    "Hello Ruby World at #{Time.now}"
  end

  run! if app_file === $0
end
