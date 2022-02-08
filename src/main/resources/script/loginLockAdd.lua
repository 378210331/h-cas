local key   = KEYS[1]
local value = ARGV[1]
local ttl   = ARGV[2]

redis.call('set',key,value,'ex',ttl,'nx')
local lock = redis.call('incr',key)
return lock
