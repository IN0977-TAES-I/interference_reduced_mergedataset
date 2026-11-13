// class name : org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder
// method name: decode(ChannelHandlerContext, Channel, ChannelBuffer)
// side       : base
// region     : lines 322-374 (affected lines: [322, 358, 360, 374])
class LengthFieldBasedFrameDecoder {
    void decode_ChannelHandlerContext__Channel__ChannelBuffer_() {
  322:  <== AFFECTED
  323:         if (frameLength < 0) {
  324:             buffer.skipBytes(lengthFieldEndOffset);
  325:             throw new CorruptedFrameException(
  326:                     "negative pre-adjustment length field: " + frameLength);
  327:         }
  328: 
  329:         frameLength += lengthAdjustment + lengthFieldEndOffset;
  330:         if (frameLength < lengthFieldEndOffset) {
  331:             buffer.skipBytes(lengthFieldEndOffset);
  332:             throw new CorruptedFrameException(
  333:                     "Adjusted frame length (" + frameLength + ") is less " +
  334:                     "than lengthFieldEndOffset: " + lengthFieldEndOffset);
  335:         }
  336: 
  337:         if (frameLength > maxFrameLength) {
  338:             // Enter the discard mode and discard everything received so far.
  339:             discardingTooLongFrame = true;
  340:             tooLongFrameLength = frameLength;
  341:             bytesToDiscard = frameLength - buffer.readableBytes();
  342:             buffer.skipBytes(buffer.readableBytes());
  343:             failIfNecessary(ctx);
  344:             return null;
  345:         }
  346: 
  347:         // never overflows because it's less than maxFrameLength
  348:         int frameLengthInt = (int) frameLength;
  349:         if (buffer.readableBytes() < frameLengthInt) {
  350:             return null;
  351:         }
  352: 
  353:         if (initialBytesToStrip > frameLengthInt) {
  354:             buffer.skipBytes(frameLengthInt);
  355:             throw new CorruptedFrameException(
  356:                     "Adjusted frame length (" + frameLength + ") is less " +
  357:                     "than initialBytesToStrip: " + initialBytesToStrip);
  358:         } <== AFFECTED
  359:         buffer.skipBytes(initialBytesToStrip);
  360:  <== AFFECTED
  361:         // extract frame
  362:         int readerIndex = buffer.readerIndex();
  363:         int actualFrameLength = frameLengthInt - initialBytesToStrip;
  364:         ChannelBuffer frame = extractFrame(buffer, readerIndex, actualFrameLength);
  365:         buffer.readerIndex(readerIndex + actualFrameLength);
  366:         return frame;
  367:     }
  368: 
  369:     private void failIfNecessary(ChannelHandlerContext ctx) {
  370:         if (bytesToDiscard == 0) {
  371:             // Reset to the initial state and tell the handlers that
  372:             // the frame was too large.
  373:             // TODO Let user choose when the exception should be raised - early or late?
  374:             //      If early, fail() should be called when discardingTooLongFrame is set to true. <== AFFECTED
    }
}
