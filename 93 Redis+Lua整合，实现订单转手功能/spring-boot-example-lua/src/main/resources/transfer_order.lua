-- Lua脚本，检查并转移订单
local orderKey = KEYS[1] -- 订单key
local currentOwner = ARGV[1] -- 当前拥有者ID
local newOwner = ARGV[2] -- 新拥有者ID

-- 获取当前订单的所有者
local currentOwnerInRedis = redis.call('GET', orderKey)

-- 检查当前所有者是否匹配
if currentOwnerInRedis == currentOwner then
    -- 更新订单所有者为新拥有者
    redis.call('SET', orderKey, newOwner)
    return1-- 返回成功标志
else
    return0-- 返回失败标志
end