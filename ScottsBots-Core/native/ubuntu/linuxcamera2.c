#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>
#include <sys/ioctl.h>
#include <jni.h>
#include "com_scottsbots_core_vision_LinuxCamera2.h"
#include <stdlib.h>
#include <stdint.h>
#include <ctype.h>
#include <string.h>
#include <strings.h>
#include <getopt.h>
#include <sys/mman.h>

#include <linux/types.h>
#include <linux/videodev.h>
#define INFILE "/dev/video0"
#define DEFAULT_OUTFILE "test.out"

/* Stole this from tvset.c */

#define READ_VIDEO_PIXEL(buf, format, depth, r, g, b)                   \
{                                                                       \
        switch (format)                                                 \
        {                                                               \
                case VIDEO_PALETTE_GREY:                                \
                        switch (depth)                                  \
                        {                                               \
                                case 4:                                 \
                                case 6:                                 \
                                case 8:                                 \
                                        (r) = (g) = (b) = (*buf++ << 8);\
                                        break;                          \
                                                                        \
                                case 16:                                \
                                        (r) = (g) = (b) =               \
                                                *((unsigned short *) buf);      \
                                        buf += 2;                       \
                                        break;                          \
                        }                                               \
                        break;                                          \
                                                                        \
                                                                        \
                case VIDEO_PALETTE_RGB565:                              \
                {                                                       \
                        unsigned short tmp = *(unsigned short *)buf;    \
                        (r) = tmp&0xF800;                               \
                        (g) = (tmp<<5)&0xFC00;                          \
                        (b) = (tmp<<11)&0xF800;                         \
                        buf += 2;                                       \
                }                                                       \
                break;                                                  \
                                                                        \
                case VIDEO_PALETTE_RGB555:                              \
                        (r) = (buf[0]&0xF8)<<8;                         \
                        (g) = ((buf[0] << 5 | buf[1] >> 3)&0xF8)<<8;    \
                        (b) = ((buf[1] << 2 ) & 0xF8)<<8;               \
                        buf += 2;                                       \
                        break;                                          \
                                                                        \
                case VIDEO_PALETTE_RGB24:                               \
                        (r) = buf[0] << 8; (g) = buf[1] << 8;           \
                        (b) = buf[2] << 8;                              \
                        buf += 3;                                       \
                        break;                                          \
                                                                        \
                default:                                                \
                        fprintf(stderr,                                 \
                                "Format %d not yet supported\n",        \
                                format);                                \
        }                                                               \
}

int fd;
int f;
char *buf;
long offset;

JNIEXPORT void JNICALL Java_com_scottsbots_core_vision_LinuxCamera2_init (JNIEnv *env, jobject this, jstring str) {

	const char *nativeString = (*env)->GetStringUTFChars(env, str, 0);
    fd = open(nativeString, O_RDONLY),f;
    (*env)->ReleaseStringUTFChars(env, str, nativeString);

}

JNIEXPORT jbyteArray JNICALL Java_com_scottsbots_core_vision_LinuxCamera2_getNativeImage  (JNIEnv *env, jobject this)
{

	unsigned char *buffer, *src;
	int bpp = 24, r, g, b;
	//int bpp = 16; // YUUV
	unsigned int i, src_depth;
	int height = 240;
	int width = 320;
	buffer = malloc(width * height * 3);
   
	if (!buffer) {
		fprintf(stderr, "Out of memory.\n");
		exit(1);
	}
	
	int length = height*width*(bpp/8);
	
	read(fd, buffer, length);
	src = buffer;

	jbyteArray jb;
	jb=(*env)->NewByteArray(env, length);
	jbyte *bytes;
	bytes = (*env)->GetByteArrayElements(env, jb, NULL);

    int k=0;
    for (i = 0; i < height*width; i++) {
		READ_VIDEO_PIXEL(src, VIDEO_PALETTE_RGB24, 24, r, g, b);
        bytes[k] =  b>>8;
        bytes[k+1] =  g>>8;
        bytes[k+2] =  r>>8;
        k=k+3;
	}
	
	(*env)->ReleaseByteArrayElements(env, jb, bytes, 0);
	free(buffer);
	return jb;
}


