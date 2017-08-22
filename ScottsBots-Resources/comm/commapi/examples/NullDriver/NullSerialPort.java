/*
 * @(#)NullSerialPort.java	1.12 98/06/25 SMI
 * 
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license
 * to use, modify and redistribute this software in source and binary
 * code form, provided that i) this copyright notice and license appear
 * on all copies of the software; and ii) Licensee does not utilize the
 * software in a manner which is disparaging to Sun.
 * 
 * This software is provided "AS IS," without a warranty of any kind.
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND
 * ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THE
 * SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS
 * BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES,
 * HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING
 * OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * This software is not designed or intended for use in on-line control
 * of aircraft, air traffic, aircraft navigation or aircraft
 * communications; or in the design, construction, operation or
 * maintenance of any nuclear facility. Licensee represents and
 * warrants that it will not use or redistribute the Software for such
 * purposes.
 */

import java.io.*;
import java.util.TooManyListenersException;
import javax.comm.*;

/**
 * Class declaration
 *
 *
 * @author
 * @version 1.9, 05/04/00
 */
class NullSerialPort extends SerialPort {

    /**
     * Constructor declaration
     *
     *
     * @param name
     *
     * @see
     */
    NullSerialPort(String name) throws IOException {
	this.name = name;
    }

    private InputStream ins;

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @throws IOException
     *
     * @see
     */
    public InputStream getInputStream() throws IOException {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return ins;
    } 

    private OutputStream outs;

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @throws IOException
     *
     * @see
     */
    public OutputStream getOutputStream() throws IOException {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return outs;
    } 

    /**
     * The following methods deal with receive thresholds
     */
    private int rcvThreshold = -1;

    /**
     * Method declaration
     *
     *
     * @param thresh
     *
     * @throws UnsupportedCommOperationException
     *
     * @see
     */
    public void enableReceiveThreshold(int thresh) 
	    throws UnsupportedCommOperationException {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	rcvThreshold = thresh;
    } 

    /**
     * Method declaration
     *
     *
     * @see
     */
    public void disableReceiveThreshold() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	rcvThreshold = -1;
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public boolean isReceiveThresholdEnabled() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return rcvThreshold != -1;
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public int getReceiveThreshold() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return rcvThreshold;
    } 

    /**
     * The following methods deal with receive timeouts
     */
    int rcvTimeout = -1;

    /**
     * Method declaration
     *
     *
     * @param rcvTimeout
     *
     * @throws UnsupportedCommOperationException
     *
     * @see
     */
    public void enableReceiveTimeout(int rcvTimeout) 
	    throws UnsupportedCommOperationException {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	this.rcvTimeout = rcvTimeout;
    } 

    /**
     * Method declaration
     *
     *
     * @see
     */
    public void disableReceiveTimeout() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	this.rcvTimeout = -1;
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public int getReceiveTimeout() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return rcvTimeout;
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public boolean isReceiveTimeoutEnabled() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return rcvTimeout == -1 ? false : true;
    } 

    /**
     * The following methods deal with receive framing
     */
    private boolean framing = false;
    private int     framingByte;
    boolean	    framingByteReceived;

    /**
     * Method declaration
     *
     *
     * @see
     */
    public void disableReceiveFraming() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	framing = false;
    } 

    /**
     * Method declaration
     *
     *
     * @param framingByte
     *
     * @throws UnsupportedCommOperationException
     *
     * @see
     */
    public void enableReceiveFraming(int framingByte) 
	    throws UnsupportedCommOperationException {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	framing = true;
	this.framingByte = framingByte & 0xff;
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public int getReceiveFramingByte() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return framingByte;
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public boolean isReceiveFramingEnabled() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return framing;
    } 

    /**
     * Method declaration
     *
     *
     * @param size
     *
     * @see
     */
    public void setInputBufferSize(int size) {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public int getInputBufferSize() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return 0;
    } 

    /**
     * Method declaration
     *
     *
     * @param size
     *
     * @see
     */
    public void setOutputBufferSize(int size) {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public int getOutputBufferSize() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return 0;
    } 

    /* Serial Port methods */
    private int baudrate;
    private int parity;
    private int dataBits;
    private int stopBits;
    private int flowcontrol;

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public int getBaudRate() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return baudrate;
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public int getDataBits() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return dataBits;
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public int getStopBits() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return stopBits;
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public int getParity() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return parity;
    } 

    /**
     * Method declaration
     *
     *
     * @param flowcontrol
     *
     * @throws UnsupportedCommOperationException
     *
     * @see
     */
    public void setFlowControlMode(int flowcontrol) 
	    throws UnsupportedCommOperationException {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	this.flowcontrol = flowcontrol;
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public int getFlowControlMode() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return flowcontrol;
    } 

    /**
     * Method declaration
     *
     *
     * @param baudrate
     * @param dataBits
     * @param stopBits
     * @param parity
     *
     * @throws UnsupportedCommOperationException
     *
     * @see
     */
    public void setSerialPortParams(int baudrate, int dataBits, int stopBits, int parity) 
	    throws UnsupportedCommOperationException {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	this.baudrate = baudrate;
	this.parity = parity;
	this.dataBits = dataBits;
	this.stopBits = stopBits;
    } 

    /**
     * Method declaration
     *
     *
     * @param millis
     *
     * @see
     */
    public void sendBreak(int millis) {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 
    } 

    private boolean dtr = true;

    /**
     * Method declaration
     *
     *
     * @param dtr
     *
     * @see
     */
    public void setDTR(boolean dtr) {
	if (closed) {
	    throw new IllegalStateException("Port Closed");

	    /*
	     * Illegal to change state of DTR when hardware flow control is on
	     */
	} 

	if ((flowcontrol & FLOWCONTROL_RTSCTS_IN) == FLOWCONTROL_RTSCTS_IN) {
	    return;
	} 

	this.dtr = dtr;
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public boolean isDTR() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return dtr;
    } 

    private boolean rts = true;

    /**
     * Method declaration
     *
     *
     * @param rts
     *
     * @see
     */
    public void setRTS(boolean rts) {
	if (closed) {
	    throw new IllegalStateException("Port Closed");

	    /*
	     * Illegal to change state of RTS when hardware flow control is on
	     */
	} 

	if ((flowcontrol & FLOWCONTROL_RTSCTS_IN) == FLOWCONTROL_RTSCTS_IN) {
	    throw new IllegalStateException("Cannot modify RTS when Hardware flowcontrol is on.");
	} 

	this.rts = rts;
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public boolean isRTS() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return rts;
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public boolean isCTS() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return true;
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public boolean isDSR() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return true;
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public boolean isRI() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return false;
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public boolean isCD() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 

	return true;
    } 

    /**
     * Method declaration
     *
     *
     * @param lsnr
     *
     * @throws TooManyListenersException
     *
     * @see
     */
    public synchronized void addEventListener(SerialPortEventListener lsnr) 
	    throws TooManyListenersException {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @see
     */
    public synchronized void removeEventListener() {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @param notify
     *
     * @see
     */
    public synchronized void notifyOnDataAvailable(boolean notify) {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @param notify
     *
     * @see
     */
    public synchronized void notifyOnOutputEmpty(boolean notify) {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @param notify
     *
     * @see
     */
    public synchronized void notifyOnCTS(boolean notify) {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @param notify
     *
     * @see
     */
    public synchronized void notifyOnDSR(boolean notify) {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @param notify
     *
     * @see
     */
    public synchronized void notifyOnCarrierDetect(boolean notify) {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @param notify
     *
     * @see
     */
    public synchronized void notifyOnRingIndicator(boolean notify) {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @param notify
     *
     * @see
     */
    public synchronized void notifyOnOverrunError(boolean notify) {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @param notify
     *
     * @see
     */
    public synchronized void notifyOnParityError(boolean notify) {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @param notify
     *
     * @see
     */
    public synchronized void notifyOnFramingError(boolean notify) {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @param notify
     *
     * @see
     */
    public synchronized void notifyOnBreakInterrupt(boolean notify) {
	if (closed) {
	    throw new IllegalStateException("Port Closed");
	} 
    } 

    /* finalizer. so we can close the comm port and release native resources */

    /**
     * Method declaration
     *
     *
     * @throws Throwable
     *
     * @see
     */
    protected void finalize() throws Throwable {}

    private boolean closed = false;

    /**
     * Method declaration
     *
     *
     * @see
     */
    public void close() {
	closed = true;

	/*
	 * WARNING *** WARNING *** WARNING *** WARNING *** WARNING *** WARNING
	 * AFTER YOU ARE DONE WITH YOUR OWN close() processing,
	 * YOU MUST MAKE THIS CALL TO CommPort's close(). OTHERWISE
	 * CommPort's HOUSEKEEPING WILL FAIL.
	 * WARNING *** WARNING *** WARNING *** WARNING *** WARNING *** WARNING
	 */
	super.close();
    } 

}




