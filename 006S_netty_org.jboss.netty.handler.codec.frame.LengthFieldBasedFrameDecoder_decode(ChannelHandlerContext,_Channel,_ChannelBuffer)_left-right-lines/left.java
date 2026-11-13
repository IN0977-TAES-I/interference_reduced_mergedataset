// class name : org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder
// method name: decode(ChannelHandlerContext, Channel, ChannelBuffer)
// side       : left
// region     : lines 322-374 (affected lines: [322, 358, 360, 374])
class LengthFieldBasedFrameDecoder {
    void decode_ChannelHandlerContext__Channel__ChannelBuffer_() {
  322:             return null; <== AFFECTED
  323:         }
  324: 
  325:         if (buffer.readableBytes() < lengthFieldEndOffset) {
  326:             return null;
  327:         }
  328: 
  329:         int actualLengthFieldOffset = buffer.readerIndex() + lengthFieldOffset;
  330:         long frameLength;
  331:         switch (lengthFieldLength) {
  332:         case 1:
  333:             frameLength = buffer.getUnsignedByte(actualLengthFieldOffset);
  334:             break;
  335:         case 2:
  336:             frameLength = buffer.getUnsignedShort(actualLengthFieldOffset);
  337:             break;
  338:         case 3:
  339:             frameLength = buffer.getUnsignedMedium(actualLengthFieldOffset);
  340:             break;
  341:         case 4:
  342:             frameLength = buffer.getUnsignedInt(actualLengthFieldOffset);
  343:             break;
  344:         case 8:
  345:             frameLength = buffer.getLong(actualLengthFieldOffset);
  346:             break;
  347:         default:
  348:             throw new Error("should not reach here");
  349:         }
  350: 
  351:         if (frameLength < 0) {
  352:             buffer.skipBytes(lengthFieldEndOffset);
  353:             throw new CorruptedFrameException(
  354:                     "negative pre-adjustment length field: " + frameLength);
  355:         }
  356: 
  357:         if (!lengthFieldIncludedInFrameLength) {
  358:             frameLength += lengthAdjustment + lengthFieldEndOffset; <== AFFECTED
  359:         }
  360:         if (frameLength < lengthFieldEndOffset) { <== AFFECTED
  361:             buffer.skipBytes(lengthFieldEndOffset);
  362:             throw new CorruptedFrameException(
  363:                     "Adjusted frame length (" + frameLength + ") is less " +
  364:                     "than lengthFieldEndOffset: " + lengthFieldEndOffset);
  365:         }
  366: 
  367:         if (frameLength > maxFrameLength) {
  368:             // Enter the discard mode and discard everything received so far.
  369:             discardingTooLongFrame = true;
  370:             tooLongFrameLength = frameLength;
  371:             bytesToDiscard = frameLength - buffer.readableBytes();
  372:             buffer.skipBytes(buffer.readableBytes());
  373:             failIfNecessary(ctx);
  374:             return null; <== AFFECTED
    }
}
