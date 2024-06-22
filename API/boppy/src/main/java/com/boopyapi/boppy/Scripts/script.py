import sys
from transformers import pipeline

# Load the pre-trained model
pipe = pipeline("text-generation", model="AmjadKha/Boppy")

# Get the text from the command-line argument
# input_text = sys.argv[1]

input_text = "Hello"
# Perform the sentiment analysis
result = pipe(input_text)

# Print the result
print(result)