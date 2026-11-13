// class name : org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder
// method name: decode(ChannelHandlerContext, Channel, ChannelBuffer)
// side       : merge
// region     : lines 322-374 (affected lines: [322, 358, 360, 374])
class LengthFieldBasedFrameDecoder {
    void decode_ChannelHandlerContext__Channel__ChannelBuffer_() {
  322:             failIfNecessary(ctx, false); <== AFFECTED
  323:             return null;
  324:         }
  325: 
  326:         if (buffer.readableBytes() < lengthFieldEndOffset) {
  327:             return null;
  328:         }
  329: 
  330:         int actualLengthFieldOffset = buffer.readerIndex() + lengthFieldOffset;
  331:         long frameLength;
  332:         switch (lengthFieldLength) {
  333:         case 1:
  334:             frameLength = buffer.getUnsignedByte(actualLengthFieldOffset);
  335:             break;
  336:         case 2:
  337:             frameLength = buffer.getUnsignedShort(actualLengthFieldOffset);
  338:             break;
  339:         case 3:
  340:             frameLength = buffer.getUnsignedMedium(actualLengthFieldOffset);
  341:             break;
  342:         case 4:
  343:             frameLength = buffer.getUnsignedInt(actualLengthFieldOffset);
  344:             break;
  345:         case 8:
  346:             frameLength = buffer.getLong(actualLengthFieldOffset);
  347:             break;
  348:         default:
  349:             throw new Error("should not reach here");
  350:         }
  351: 
  352:         if (frameLength < 0) {
  353:             buffer.skipBytes(lengthFieldEndOffset);
  354:             throw new CorruptedFrameException(
  355:                     "negative pre-adjustment length field: " + frameLength);
  356:         }
  357: 
  358:         if (!lengthFieldIncludedInFrameLength) { <== AFFECTED
  359:             frameLength += lengthAdjustment + lengthFieldEndOffset;
  360:         } <== AFFECTED
  361:         if (frameLength < lengthFieldEndOffset) {
  362:             buffer.skipBytes(lengthFieldEndOffset);
  363:             throw new CorruptedFrameException(
  364:                     "Adjusted frame length (" + frameLength + ") is less " +
  365:                     "than lengthFieldEndOffset: " + lengthFieldEndOffset);
  366:         }
  367: 
  368:         if (frameLength > maxFrameLength) {
  369:             // Enter the discard mode and discard everything received so far.
  370:             discardingTooLongFrame = true;
  371:             tooLongFrameLength = frameLength;
  372:             bytesToDiscard = frameLength - buffer.readableBytes();
  373:             buffer.skipBytes(buffer.readableBytes());
  374:             failIfNecessary(ctx, true); <== AFFECTED
    }
}
