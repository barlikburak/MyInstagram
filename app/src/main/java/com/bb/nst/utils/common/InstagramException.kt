package com.bb.nst.utils.common

/**
 * Errors
 */
open class InstagramException(message: String) : Exception(message)

private const val DEFAULT_NETWORK_OPERATION_EXCEPTION_MESSAGE = "Network operation error."
private const val DEFAULT_ACCESS_DENIED_EXCEPTION_MESSAGE = "The user denied your request."

class InstagramAuthAccessDeniedException(message: String = DEFAULT_ACCESS_DENIED_EXCEPTION_MESSAGE) : InstagramException(message)
class InstagramAuthNetworkOperationException(message: String = DEFAULT_NETWORK_OPERATION_EXCEPTION_MESSAGE) : InstagramException(message)