# Introduction to Spring AI

This repository contains source code examples used to support my learning courses about the Spring Framework.

## Content
This repository has basic controller and service .
* Controller has few endpoints to call service.
* service is connecting with OPEN AI
* service is returing data as string , or as json.
* there are few endpoints
  * First end point to ask any question.
  * Second end point to check capital of country or state
  * Third endpoint to use prompt template to get country or state
  * Fourth endpoint to get json response to get country or state
  * Fifteh endpoint where you can input text and prompt and result will be based on the prompt
  * sixth endpoint to use function for callback , using Ninja API to get weather provided lat and long

* New RAg endpoint added which is using required document to create data and if data is not there retunring no data found

## Image model
Image model is used to gernearte image.
New endpoint created which will accept image related question and generated the image.

                
## WHAT IS RAG
Retrieval-augmented generation (RAG) is a technique for enhancing the accuracy and reliability of generative AI models with facts fetched from external sources.
in our case external source is provided data.

RAG (Retrieval-Augmented Generation) is an AI framework that combines the strengths of traditional information retrieval systems (such as search and databases) with the capabilities of generative large language models (LLMs). By combining your data and world knowledge with LLM language skills, grounded generation is more accurate, up-to-date, and relevant to your specific needs.
  
