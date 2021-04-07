require 'sinatra/base'

class MyApp < Sinatra::Base
  configure do
    set :environment, :production
    set :port, ENV['FUNCTIONS_CUSTOMHANDLER_PORT']
  end

  post '/TimerExample' do
    'Timer Trigger Example'
  end

  get '/api/Hello' do
    "Hello Ruby World at #{Time.now}"
  end

  run! if app_file === $0
end
