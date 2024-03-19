package com.rm.android_fundamentals.topics.t8_coroutinesflow.utils

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.lang.RuntimeException
import kotlin.random.Random

class MockNetworkInterceptor : Interceptor {

    private val mockResponses = mutableListOf<MockResponse>()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val mockResponse = findMockResponseInList(request)
            ?: throw RuntimeException("No mock response found for url ${request.url()}. Please define a mock response in your MockApi!")

        removeResponseIfItShouldNotBePersisted(mockResponse)
        simulateNetworkDelay(mockResponse)

        return if (mockResponse.status < 400) {
            if (mockResponse.errorFrequencyInPercent == 0) {
                createSuccessResponse(request, mockResponse)
            } else {
                maybeReturnErrorResponse(request, mockResponse)
            }
        } else {
            createErrorResponse(request)
        }
    }

    private fun findMockResponseInList(request: Request) : MockResponse? {
        return mockResponses.find {
            it.path.contains(request.url().encodedPath())
        }
    }

    private fun removeResponseIfItShouldNotBePersisted(mockResponse: MockResponse) {
        if (!mockResponse.persist) {
            mockResponses.remove(mockResponse)
        }
    }

    private fun simulateNetworkDelay(mockResponse: MockResponse) =
        Thread.sleep(mockResponse.delayInMs)

    private fun maybeReturnErrorResponse(
        request: Request,
        mockResponse: MockResponse
    ) = when (Random.nextInt(0, 101)) {
        in 0..mockResponse.errorFrequencyInPercent -> createErrorResponse(request)
        else -> createSuccessResponse(request, mockResponse)
    }

    private fun createErrorResponse(
        request: Request,
        errorBody: String = "Error"
    ): Response {
        return Response.Builder()
            .code(500)
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("Internal Server Error: $errorBody")
            .body(
                ResponseBody.create(
                    MediaType.get("text/plain"),
                    errorBody
                )
            )
            .build()
    }

    private fun createSuccessResponse(
        request: Request,
        mockResponse: MockResponse
    ): Response {
        return Response.Builder()
            .code(mockResponse.status)
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("OK")
            .body(
                ResponseBody.create(
                    MediaType.get("application/json"),
                    mockResponse.body.invoke()
                )
            )
            .build()
    }

    fun mock(
        path: String,
        body: () -> String,
        status: Int,
        delayInMs: Long = 250,
        persist: Boolean = true,
        errorFrequencyInPercent: Int = 0
    ) = apply {
        val mockResponse = MockResponse(
            path,
            body,
            status,
            delayInMs,
            persist,
            errorFrequencyInPercent
        )
        mockResponses.add(mockResponse)
    }
}

data class MockResponse(
    val path: String,
    val body: () -> String,
    val status: Int,
    val delayInMs: Long,
    val persist: Boolean,
    val errorFrequencyInPercent: Int
)