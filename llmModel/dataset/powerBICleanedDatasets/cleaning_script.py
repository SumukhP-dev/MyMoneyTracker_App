import pandas as pd
import json

# dataset is provided by Power BI as input DataFrame
df = dataset.copy()

# Make sure it's sorted properly
df = df.sort_values(by="Index").reset_index(drop=True)

conversations = []
i = 0
while i < len(df):
    # Look for user row
    if df.loc[i, "role"] == "user":
        if i + 1 < len(df) and df.loc[i+1, "role"] == "assistant":
            user_msg = {
                "role": "user",
                "content": "Can you generate a financial tip based on this transaction?",
                "context": df.loc[i, "content"],
            }
            assistant_msg = {
                "role": "assistant",
                "content": df.loc[i+1, "content"]
            }
            conversations.append({"messages": [user_msg, assistant_msg]})
            i += 2  # jump over the pair
        else:
            i += 1
    else:
        i += 1

# Convert to DataFrame so Power BI can read the result
result = pd.DataFrame({
    "conversation_json": [json.dumps(conv, indent=2) for conv in conversations]
})