#!/usr/bin/env ruby
require 'net/http'

uris = [
  'http://localhost:8080/hawkular/status',
  'http://localhost:8080/hawkular/metrics/status',
  'http://localhost:8080/hawkular/alerts/status',
  'http://localhost:8080/hawkular/inventory/status'
]

uris.each do |uri_string|
  loop do
    uri = URI(uri_string)
    begin
      response = Net::HTTP.get_response(uri)
      break if response.code == '200'
      puts "Waiting for: #{uri_string}"
    rescue
      puts 'Waiting for Hawkular-Services to accept connections'
    end
    sleep 5
  end
end
