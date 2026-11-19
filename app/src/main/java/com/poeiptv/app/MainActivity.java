EditText url = new EditText(this);
url.setHint("COLE SUA URL M3U AQUI");
url.setTextColor(Color.WHITE);
url.setHintTextColor(Color.LTGRAY);
url.setSingleLine(true);
url.setTextSize(16);
url.setPadding(20, 15, 20, 15);
url.setBackgroundColor(Color.rgb(55, 65, 80));
url.setInputType(InputType.TYPE_CLASS_TEXT);
url.setVisibility(android.view.View.VISIBLE);

layout.addView(url,
    new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
);
