require 'sinatra'

set :bind, '0.0.0.0'
set :port, ENV['FUNCTIONS_CUSTOMHANDLER_PORT']

get '/api/Hello' do
  'Hello Ruby World'
end
