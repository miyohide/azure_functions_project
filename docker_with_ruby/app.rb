require 'sinatra'

set :environment, :production
set :port, ENV['FUNCTIONS_CUSTOMHANDLER_PORT']

get '/api/Hello' do
  'Hello Ruby World'
end
