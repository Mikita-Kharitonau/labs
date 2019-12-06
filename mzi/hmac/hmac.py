import hashlib


def hmac(key, message, digest):
    inner, outer = digest(), digest()

    if len(key) > inner.block_size:
        key = digest(key).digest()
    key = key.ljust(inner.block_size, b'\x00')

    b = 256
    opad = bytes((x ^ 0x5C) for x in range(b))
    ipad = bytes((x ^ 0x36) for x in range(b))

    inner.update(key.translate(ipad))
    inner.update(message)

    outer.update(key.translate(opad))
    outer.update(inner.digest())
    return outer.digest()


if __name__ == '__main__':
    key = b'keykeykeykeykey'
    message = b'messagemessagemessage'
    digest = hmac(key, message, hashlib.md5)
    print(digest)
