// class name : org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder
// method name: decode(ChannelHandlerContext, Channel, ChannelBuffer)
// side       : right
// region     : lines 322-374 (affected lines: [322, 358, 360, 374])
class LengthFieldBasedFrameDecoder {
    void decode_ChannelHandlerContext__Channel__ChannelBuffer_() {
  322:         } <== AFFECTED
  323: 
  324:         if (frameLength < 0) {
  325:             buffer.skipBytes(lengthFieldEndOffset);
  326:             throw new CorruptedFrameException(
  327:                     "negative pre-adjustment length field: " + frameLength);
  328:         }
  329: 
  330:         frameLength += lengthAdjustment + lengthFieldEndOffset;
  331:         if (frameLength < lengthFieldEndOffset) {
  332:             buffer.skipBytes(lengthFieldEndOffset);
  333:             throw new CorruptedFrameException(
  334:                     "Adjusted frame length (" + frameLength + ") is less " +
  335:                     "than lengthFieldEndOffset: " + lengthFieldEndOffset);
  336:         }
  337: 
  338:         if (frameLength > maxFrameLength) {
  339:             // Enter the discard mode and discard everything received so far.
  340:             discardingTooLongFrame = true;
  341:             tooLongFrameLength = frameLength;
  342:             bytesToDiscard = frameLength - buffer.readableBytes();
  343:             buffer.skipBytes(buffer.readableBytes());
  344:             failIfNecessary(ctx, true);
  345:             return null;
  346:         }
  347: 
  348:         // never overflows because it's less than maxFrameLength
  349:         int frameLengthInt = (int) frameLength;
  350:         if (buffer.readableBytes() < frameLengthInt) {
  351:             return null;
  352:         }
  353: 
  354:         if (initialBytesToStrip > frameLengthInt) {
  355:             buffer.skipBytes(frameLengthInt);
  356:             throw new CorruptedFrameException(
  357:                     "Adjusted frame length (" + frameLength + ") is less " +
  358:                     "than initialBytesToStrip: " + initialBytesToStrip); <== AFFECTED
  359:         }
  360:         buffer.skipBytes(initialBytesToStrip); <== AFFECTED
  361: 
  362:         // extract frame
  363:         int readerIndex = buffer.readerIndex();
  364:         int actualFrameLength = frameLengthInt - initialBytesToStrip;
  365:         ChannelBuffer frame = extractFrame(buffer, readerIndex, actualFrameLength);
  366:         buffer.readerIndex(readerIndex + actualFrameLength);
  367:         return frame;
  368:     }
  369: 
  370:     private void failIfNecessary(ChannelHandlerContext ctx, boolean firstDetectionOfTooLongFrame) {
  371:         if (bytesToDiscard == 0) {
  372:             // Reset to the initial state and tell the handlers that
  373:             // the frame was too large.
  374:             long tooLongFrameLength = this.tooLongFrameLength; <== AFFECTED
    }
}
