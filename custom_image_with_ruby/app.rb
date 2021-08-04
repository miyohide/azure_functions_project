require 'sinatra/base'
require 'json'
class MyApp < Sinatra::Base
  configure do
    enable :logging
    set :environment, :production
    set :port, ENV['FUNCTIONS_CUSTOMHANDLER_PORT']
  end

  post '/TimerExample' do
    logger.info "----- Timer Schedule Start"
    header_data = request.env
    body_data = JSON.parse(request.body.read)
    content_type :json
    data = {
      "Outputs" => {
        "res" => { "body" => "abc" }
      },
      "Logs" => [header_data.to_s, body_data.to_s],
      "ReturnValue" => "hogehoge"
    }
    logger.info "----- Timer Schedule End"
    data.to_json
  end

  get '/api/Hello' do
    "Hello Ruby World at #{Time.now}"
  end

  run! if app_file === $0
end
