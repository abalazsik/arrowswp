#pragma version(1)

#pragma rs java_package_name(org.abalazsik.arrowswp)

rs_allocation gOut;
rs_allocation gIn;

typedef struct kpoint {
    float x;
    float y;

    float2 basei;
    float2 basej;
} kpoint_;

const float DIST =  10;
struct kpoint *kpoints;
float srcCenterX;
float srcCenterY;
float dstCenterX;
float dstCenterY;
uchar4 backgroundColor;
int kpointsSize;
bool wrapbackground;

static uint32_t closest(uint32_t x, uint32_t y) {
    float minDist2 = (kpoints[0].x - x) * (kpoints[0].x - x) + (kpoints[0].y - y) * (kpoints[0].y - y);
    uint32_t minIdx = 0;

    for (uint32_t i = 1 ; i < kpointsSize; i++) {
		float tmp = (kpoints[i].x - x) * (kpoints[i].x - x) + (kpoints[i].y - y) * (kpoints[i].y - y);
		if (tmp < minDist2) {
			minDist2 = tmp;
			minIdx = i;
		}
	}

	return minIdx;
}

static float2 gaussElimination(float2 baseI, float2 baseJ, float x, float y) {

    if (baseI.y == 0 && baseJ.x == 0) {
        float2 result;
        result.x = x;
        result.y = y;
        return result;
    }

	if (baseI.x == 0) {
		float2 tmp = baseI;
		baseI = baseJ;
		baseJ = tmp;
		float t = x;
		x = y;
		y = t;
	}

	float a = baseI.x;
	float b = baseI.y;

	float c = baseJ.x;
	float d = baseJ.y;

	float2 result;

	//gauss elimination. Only do the necessary calculations

	if(a != 0) {
		b /= a;
		x /= a;
	} else {
		x /= b;
		//b = 1;

		y -= d * x;
		//d = 0;
		y /= c;
		//c = 1;

        result.x = x;
        result.y = y;

		return result;
	}
	//a = 1f;

	if (c != 0){
		d /= c;
		y /= c;
	}
	//c = 1f;

	//c = 0;// c -= a
	d -= b;
	y -= x;

	y /= d;
	//d = 1f;

	x -= b * y;
	//b = 0 //b -= b * 1

    result.x = x;
    result.y = y;

	return result;
}

void RS_KERNEL transform(uchar4 blendRGB, uint32_t x, uint32_t y) {

    struct kpoint oxl = kpoints[closest(x, y)];

    float2 point = gaussElimination(
        oxl.basei,
        oxl.basej,
        (float)(x - dstCenterX),
        (float)(y - dstCenterY));

    int srcX = round(point.x + srcCenterX);
    int srcY = round(point.y + srcCenterY);

    const uint32_t width = rsAllocationGetDimX(gOut);
    const uint32_t height = rsAllocationGetDimY(gOut);

    if (srcX >= 0 && srcX < width && srcY >= 0 && srcY < height) {
        rsSetElementAt_uchar4(gOut, rsGetElementAt_uchar4(gIn, srcX, srcY), x, y);
    } else if (wrapbackground) {
    	uint32_t sx = (srcX + width) % width;
    	uint32_t sy = (srcY + height) % height;

        rsSetElementAt_uchar4(gOut, rsGetElementAt_uchar4(gIn, sx, sy), x, y);
    } else {
        rsSetElementAt_uchar4(gOut, backgroundColor, x, y);
   	}

}