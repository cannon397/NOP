-- local redisHash =
local key = KEYS[1]
local primaryId = KEYS[2]
local dataMap = KEYS[3]
local eventKey = KEYS[4]

local remainEventLimitStr = redis.call('HGET', eventKey, 'joinLimit')
local remainEventLimit = tonumber(remainEventLimitStr)

if remainEventLimit and remainEventLimit > 0 then
    local isSaved = redis.call('SADD', key, primaryId)
        if isSaved == 1 then
            redis.call('HINCRBY', eventKey, 'joinLimit', -1)
            redis.call('RPUSH', key .. ':list',   dataMap)
            return '{"success":true,"message":""}'
        else
            return '{"success":false,"message":"4092"}'
        end
else
    return '{"success":false,"message":"4093"}'
end
