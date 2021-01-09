#pragma version(1)

#pragma rs java_package_name(org.abalazsik.arrowswp)

rs_allocation gOut;

void RS_KERNEL blend(uchar4 blendRGB, uint32_t x, uint32_t y) {
    uchar4 srcRGB = rsGetElementAt_uchar4(gOut, x, y);

    uchar4 out = srcRGB;

    out.a = max(srcRGB.a, blendRGB.a);
    out.r = max(srcRGB.r, blendRGB.r);
    out.g = max(srcRGB.g, blendRGB.g);
    out.b = max(srcRGB.b, blendRGB.b);

    rsSetElementAt_uchar4(gOut, out, x, y);
}