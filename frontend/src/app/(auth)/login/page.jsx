'use client';
import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/hooks/useAuth';
import { Button } from '@/components/ui/Button';
import { Input } from '@/components/ui/Input';

export default function LoginPage() {
    const router = useRouter();
    const { login, error, clearError } = useAuth();
    const [formData, setFormData] = useState({
        username: '',
        password: '',
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }));
        if (error) clearError();
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const success = await login(formData.username, formData.password);
        if (success) {
            router.push('/');
        }
    };

    return (
      <div className="min-h-screen flex items-center justify-center">
          <div className="max-w-md w-full space-y-8 p-8 bg-white rounded-lg shadow">
              <div>
                  <h2 className="text-center text-3xl font-extrabold text-gray-900">
                      Sign in to your account
                  </h2>
              </div>
              <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
                  <div className="space-y-4">
                      <Input
                        label="Username"
                        name="username"
                        type="text"
                        required
                        value={formData.username}
                        onChange={handleChange}
                      />
                      <Input
                        label="Password"
                        name="password"
                        type="password"
                        required
                        value={formData.password}
                        onChange={handleChange}
                      />
                  </div>
                  {error && (
                    <div className="text-red-500 text-center text-sm">{error}</div>
                  )}
                  <Button type="submit" variant="primary" className="w-full">
                      Sign in
                  </Button>
              </form>
          </div>
      </div>
    );
}
