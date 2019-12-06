from codecs import getdecoder
from codecs import getencoder
from os import urandom
from hashlib import md5


def hex_decode(data):
    return getdecoder('hex')(data)[0]


def hex_encode(data):
    return getencoder('hex')(data)[0].decode('ascii')


def str_xor(a, b):
    return bytes(a_ ^ b_ for a_, b_ in zip(bytearray(a), bytearray(b)))


def bytes_to_long(raw):
    return int(hex_encode(raw), 16)


def long_to_bytes(n, size=32):
    res = hex(int(n))[2:].rstrip('L')
    if len(res) % 2 != 0:
        res = '0' + res
    s = hex_decode(res)
    if len(s) != size:
        s = (size - len(s)) * b"\x00" + s
    return s


def mod_invert(a, n):
    if a < 0:
        return n - mod_invert(-a, n)
    t, new_t = 0, 1
    r, new_r = n, a
    while new_r:
        quotinent = r // new_r
        t, new_t = new_t, t - quotinent * new_t
        r, new_r = new_r, r - quotinent * new_r
    if r > 1:
        return -1
    if t < 0:
        t += n
    return t


def public_key(curve, private_key):
    return curve.scalar_multiply(private_key)


def private_key(q):
    key = bytes_to_long(urandom(32))
    while not key or key >= bytes_to_long(q):
        key = bytes_to_long(urandom(32))
    return key


class Curve:
    def __init__(self, p, q, a, b, x, y):
        self.p = bytes_to_long(p)
        self.q = bytes_to_long(q)
        self.a = bytes_to_long(a)
        self.b = bytes_to_long(b)
        self.x = bytes_to_long(x)
        self.y = bytes_to_long(y)

        r1 = self.y * self.y % self.p
        r2 = ((self.x * self.x + self.a) * self.x + self.b) % self.p
        if r2 < 0:
            r2 += self.p
        if r1 != r2:
            raise ValueError('Invalid parameters')

    def _pos(self, v):
        if v < 0:
            return v + self.p
        return v

    def _add(self, p1x, p1y, p2x, p2y):
        if p1x == p2x and p1y == p2y:
            t = ((3 * p1x * p1x + self.a) * mod_invert(2 * p1y, self.p)) % self.p
        else:
            tx = self._pos(p2x - p1x) % self.p
            ty = self._pos(p2y - p1y) % self.p
            t = (ty * mod_invert(tx, self.p)) % self.p

        tx = self._pos(t * t - p1x - p2x) % self.p
        ty = self._pos(t * (p1x - tx) - p1y) % self.p
        return tx, ty

    def scalar_multiply(self, degree, x=None, y=None):
        x = x or self.x
        y = y or self.y
        tx = x
        ty = y
        degree -= 1
        if degree == 0:
            raise ValueError("Bad degree value")
        while degree != 0:
            if degree & 1 == 1:
                tx, ty = self._add(tx, ty, x, y)
            degree = degree >> 1
            x, y = self._add(x, y, x, y)
        return tx, ty


MODE_SIZE = 32

CURVE = (
    hex_decode('FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFD97'),
    hex_decode('FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF6C611070995AD10045841B09B761B893'),
    hex_decode('FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFD94'),
    hex_decode('00000000000000000000000000000000000000000000000000000000000000a6'),
    hex_decode('0000000000000000000000000000000000000000000000000000000000000001'),
    hex_decode('8D91E471E0989CDA27DF505A453F2B7635294F2DDF23E3B122ACC99C9E9F1E14'),
)


def sign(curve, private_key, digest):
    q = curve.q
    e = bytes_to_long(digest) % q
    if e == 0:
        e = 1
    while True:
        k = bytes_to_long(urandom(MODE_SIZE)) % q
        if k == 0:
            continue

        r, _ = curve.scalar_multiply(k)
        r %= q
        if r == 0:
            continue

        d = private_key * r
        k *= e
        s = (d + k) % q
        if s == 0:
            continue

        return long_to_bytes(s, MODE_SIZE) + long_to_bytes(r, MODE_SIZE)


def verify(curve, pub, digest, signature):
    q = curve.q
    p = curve.p
    s = bytes_to_long(signature[:MODE_SIZE])
    r = bytes_to_long(signature[MODE_SIZE:])
    if not 0 < r < q or not 0 < s < q:
        return False

    e = bytes_to_long(digest) % curve.q
    if e == 0:
        e = 1
    v = mod_invert(e, q)
    z1 = s * v % q
    z2 = q - r * v % q
    p1x, p1y = curve.scalar_multiply(z1)
    q1x, q1y = curve.scalar_multiply(z2, pub[0], pub[1])

    delta_x = p1x - q1x
    m = (p1y - q1y) * mod_invert(delta_x, p)
    x_c = (m ** 2 - p1x - q1x) % p
    if x_c < 0:
        x_c += p

    R = x_c % q
    return R == r


if __name__ == '__main__':
    message = b"messagemessagemessage"

    p, q, a, b, x, y = CURVE
    curve = Curve(p, q, a, b, x, y)

    private_key = private_key(q)
    signature = sign(curve, private_key, md5(message).digest())
    pub = public_key(curve, private_key)
    is_verified = verify(curve, pub, md5(message).digest(), signature)

    print('signature', signature)
    print('pub', pub)
    print('is_verified', is_verified)
