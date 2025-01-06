import '../styles/globals.css';

export const metadata = {
  title: 'Movie App',
  description: 'Your favorite movie database',
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
    <body>
    <div className="min-h-screen bg-gray-50">{children}</div>
    </body>
    </html>
  );
}
